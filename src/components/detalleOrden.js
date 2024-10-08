window.onload = function() {
    const ordenSeleccionada = JSON.parse(sessionStorage.getItem("ordenSeleccionada"));
    const idOrden = ordenSeleccionada.idOrden;

    if (ordenSeleccionada){
        crearTablaEnvio(ordenSeleccionada);
    }else{
        console.error("No se encontró ninguna orden seleccionada.");
    }

    fetchData(idOrden);
}

function crearTablaEnvio(orden) {
    const contenedorEnvio = document.getElementById("container-Envio");
    const table = document.createElement("table");

    const trHeader = document.createElement("tr");
    trHeader.innerHTML = `
        <th>Nombre</th>
        <th>Apellido</th>
        <th>Teléfono</th>
        <th>Dirección</th>
        <th>Código Postal</th>
        <th>Ciudad</th>
        <th>Modalidad de Entrega</th>
        <th>Fecha</th>
        <th>Método de Pago</th>
        <th>Referencias</th>
        <th>Agregar evidencia de pago </th>

    `;
    table.appendChild(trHeader);

    let inputEvidencia = `<td>${orden.image_Evidencia}</td>`
    if (orden.image_Evidencia == null) {
        inputEvidencia = `
        <td>
            <input type="text" id="input-evidencia">
            <button id="${orden.idOrden}" class="botonEnviar">Subir</button>
        </td>
        `;
    }

    const tr = document.createElement("tr");
    tr.innerHTML = `
        <td>${orden.cedula.nombre}</td>
        <td>${orden.idEnvio.apellido}</td>
        <td>${orden.idEnvio.telefono}</td>
        <td>${orden.idEnvio.direccion}</td>
        <td>${orden.idEnvio.codigoPostal}</td>
        <td>${orden.idEnvio.idCiudad.nombre}</td>
        <td>${orden.idEnvio.modalidadEntrega}</td>
        <td>${orden.fecha}</td>
        <td>${orden.metodoPago}</td>
        <td>${orden.idEnvio.referencias ? orden.idEnvio.referencias : "No hay referencias"}</td>
        ${inputEvidencia}

    `;

    table.appendChild(tr);

    contenedorEnvio.appendChild(table);
    asignarImagenBoton();
}
function asignarImagenBoton(){
    const botonAsignar = document.querySelectorAll(".botonEnviar");
    botonAsignar.forEach(boton => {
        boton.addEventListener("click", function(e) {
            const idOrden = e.target.id;
            const imagenAsignada = document.getElementById("input-evidencia").value;
            asignarEvidenciaPago(idOrden, imagenAsignada);
        });
    });
}


function fetchData(idOrden){
    const url = `http://localhost:8081/Apiweb/v1/ordenProd/visualizarProductos/${idOrden}`
    fetch(url)
    .then(response => response.json())
    .then(data => {
        mostrarProductos(data);
    })
    .catch(error => console.error(error));
}

function mostrarProductos(data){
    const contenedorProductos = document.getElementById("container-producto");
    const table = document.createElement("table");

    const trHeader = document.createElement("tr");
    trHeader.innerHTML = `
        <th>Imagen</th>
        <th>Nombre</th>
        <th>Género</th>
        <th>Precio Unitario</th>
        <th>Personalizable</th>
        <th>Ver Personalización</th>
    `;
    table.appendChild(trHeader);

    data.forEach(ordenProd => { 
        const producto = ordenProd.idProducto;
        const tr = document.createElement("tr");
        tr.innerHTML = `
            <td><img src="${producto.image_Url}" alt="${producto.nombre}"></td>
            <td>${producto.nombre}</td>
            <td>${producto.genero}</td>
            <td>${producto.precio}</td>
            <td>${producto.personalizable}</td>
            <td>
                <button class="btn-a-disenoP" id="${ordenProd.idOrdenProd}" ${producto.personalizable !== "si" ? 'style="display: none;"' : ''}>Ver personalización</button>
            </td>
        `;
        table.appendChild(tr);

        const botonADisenoP = tr.querySelector(".btn-a-disenoP");
        botonADisenoP.addEventListener("click", function() {
            sessionStorage.setItem("ordenProdSeleccionada", JSON.stringify(ordenProd));
            window.location.href = "../pages/detallesDisenoPOrden.html";
        });
    });

    contenedorProductos.appendChild(table);
}

function asignarEvidenciaPago(idOrden, imagenAsignada){
    const estado = "pagada";
    const urlImagen = `http://localhost:8081/Apiweb/v1/orden/actualizarImagenEvidenciaPagoyEstado/${idOrden}/${estado}`;
    fetch(urlImagen, {
        method: 'PUT',
        body: imagenAsignada,
        headers:{
            'Content-Type': 'application/json'
        }
    }).then(response => response)
    .then(() => {

        let ordenSeleccionada = JSON.parse(sessionStorage.getItem("ordenSeleccionada"));
        ordenSeleccionada.image_Evidencia = imagenAsignada;
        sessionStorage.setItem("ordenSeleccionada", JSON.stringify(ordenSeleccionada));
        
        Toastify({
            text: "Evidencia asignada correctamente",
            duration: 1000,
            close: true,
            gravity: "top",
            position: "right",
            stopOnFocus: true,
            style: {
                background: "linear-gradient(to right, #4b33a8, #785ce9)",
                borderRadius: "2rem",
            }
        }).showToast();
        setTimeout(() => {
            location.reload();
        }, 1000);
    })
    .catch(error => console.error(error));
}