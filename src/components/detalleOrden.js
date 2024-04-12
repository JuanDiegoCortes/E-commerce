
window.onload = function() {
    const contenedorProductos = document.getElementById("container-producto");
    const ordenSeleccionada = JSON.parse(sessionStorage.getItem("ordenSeleccionada"));
    console.log(ordenSeleccionada)
    if (ordenSeleccionada){
        document.getElementById("nombreOrden").innerText += ordenSeleccionada.idOrden.cedula.nombre;
        document.getElementById("apellidoOrden").innerText += ordenSeleccionada.idOrden.idEnvio.apellido;
        document.getElementById("telefonoOrden").innerText += ordenSeleccionada.idOrden.idEnvio.telefono;
        document.getElementById("direccionOrden").innerText += ordenSeleccionada.idOrden.idEnvio.direccion;
        document.getElementById("codigo-postalOrden").innerText += ordenSeleccionada.idOrden.idEnvio.codigoPostal;
        document.getElementById("ciudadOrden").innerText += ordenSeleccionada.idOrden.idEnvio.idCiudad.nombre;
        document.getElementById("modalidad-entregaOrden").innerText += ordenSeleccionada.idOrden.idEnvio.modalidadEntrega;
        document.getElementById("fechaOrden").innerText += ordenSeleccionada.idOrden.fecha;
        if(ordenSeleccionada.idOrden.idEnvio.referencias === null){
            document.getElementById("referenciasOrden").innerText += "No hay referencias";
        }else{
            document.getElementById("referenciasOrden").innerText += ordenSeleccionada.idOrden.idEnvio.referencias;
        }
    }else{
        console.error("No se encontró ninguna orden seleccionada.");
    }
    contenedorProductos.innerHTML = "";
    ordenSeleccionada.forEach(orden => {
    const producto = orden.idOrden.idProducto;

    const div = document.createElement("div");
    div.classList.add("producto");
    div.innerHTML = `
    <img class="producto-imagen" src="${producto.image_Url}" alt="${producto.nombre}">
    <div class="producto-detalles">
        <h3 class="producto-titulo">${producto.nombre}</h3>
        <p class="producto-personalizable">Personalizable: ${producto.personalizable}</p>
        <p class="producto-precio">Precio: ${producto.precio}</p>
    </div>
    `;
    contenedorProductos.append(div);
    }); 
}


