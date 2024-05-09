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
    `;
    table.appendChild(trHeader);

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
    `;
    table.appendChild(tr);

    contenedorEnvio.appendChild(table);
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