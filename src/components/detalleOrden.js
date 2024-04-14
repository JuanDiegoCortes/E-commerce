window.onload = function() {
    const ordenSeleccionada = JSON.parse(sessionStorage.getItem("ordenSeleccionada"));
    const idOrden = ordenSeleccionada.idOrden;
    const contenedorProductos = document.getElementById("container-producto");

    if (ordenSeleccionada){
        document.getElementById("nombreOrden").innerText += ordenSeleccionada.cedula.nombre;
        document.getElementById("apellidoOrden").innerText += ordenSeleccionada.idEnvio.apellido;
        document.getElementById("telefonoOrden").innerText += ordenSeleccionada.idEnvio.telefono;
        document.getElementById("direccionOrden").innerText += ordenSeleccionada.idEnvio.direccion;
        document.getElementById("codigo-postalOrden").innerText += ordenSeleccionada.idEnvio.codigoPostal;
        document.getElementById("ciudadOrden").innerText += ordenSeleccionada.idEnvio.idCiudad.nombre;
        document.getElementById("modalidad-entregaOrden").innerText += ordenSeleccionada.idEnvio.modalidadEntrega;
        document.getElementById("fechaOrden").innerText += ordenSeleccionada.fecha;
        if(ordenSeleccionada.idEnvio.referencias === null){
            document.getElementById("referenciasOrden").innerText += "No hay referencias";
        }else{
            document.getElementById("referenciasOrden").innerText += ordenSeleccionada.idEnvio.referencias;
        }
    }else{
        console.error("No se encontró ninguna orden seleccionada.");
    }

    function fetchData(idOrden){
        const url = `http://localhost:8081/Apiweb/v1/ordenProd/visualizarProductos/${idOrden}`
        fetch(url)
        .then(response => response.json())
        .then(data => {
            ordenProd = data;
            console.log(ordenProd);
            mostrarProductos(ordenProd);
        })
        .catch(error => console.error(error));
    }

    function mostrarProductos(data){
        data.forEach(ordenProd => { 
            const producto = ordenProd.idProducto;
            const div = document.createElement("div");
            div.classList.add("producto");
            div.innerHTML = `
                <img class="producto-imagen" src="${producto.image_Url}" alt="${producto.nombre}">
                <div class="producto-detalles">
                <h3 class="producto-titulo">${producto.nombre}</h3>
                <h3 class="producto-genero">Genero: ${producto.genero}</h3>
                <p class="producto-precio">Precio unitario: ${producto.precio}</p>
                <p class="producto-personalizable">Personalizable: ${producto.personalizable}</p>
                <button class="btn-a-disenoP" id="${ordenProd.idOrdenProd}">Ver personalización</button>
                </div>
                `;
            if (producto.personalizable !== "si") {
                const botonPersonalizar = div.querySelector('.btn-a-disenoP');
                botonPersonalizar.style.display = "none"; // Oculta el botón de personalización para productos no personalizables
            }
            contenedorProductos.append(div);

            const botonADisenoP = div.querySelector(".btn-a-disenoP");
            botonADisenoP.addEventListener("click", function() {
                sessionStorage.setItem("ordenProdSeleccionada", JSON.stringify(ordenProd));
                window.location.href = "../pages/detallesDisenoPOrden.html";
            });
        });
    }
    fetchData(idOrden);
}


