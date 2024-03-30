let ordenes = [];

function fetchData() {
    return fetch("http://localhost:8081/Apiweb/v1/orden/")
        .then(response => response.json())
        .then(data => {
            ordenes = data;
            cargarOrdenes(ordenes);
        })
        .catch(error => {
            console.error("Error al obtener los datos:", error);
        });
}

function cargarOrdenes(ordenesElegidas) {
    console.log(ordenesElegidas);
    const contenedorOrden = document.getElementById("contenedor-orden");

    if (contenedorOrden) {
        contenedorOrden.innerHTML = "";

        ordenesElegidas.forEach(orden => {
            const div = document.createElement("div");
            div.classList.add("orden");
            div.innerHTML = `
                <p>ID de Orden: ${orden.idOrden}</p>
                <p>Fecha: ${orden.fecha}</p>
                <p>Estado: ${orden.estado}</p>
                <p>Método de Pago: ${orden.metodoPago}</p>
                <p>Precio Total: ${orden.precioTotal}</p>
            `;
            contenedorOrden.append(div);
        });
    } else {
        console.error("El elemento con id 'contenedor-orden' no existe.");
    }
}

fetchData();

/* <p>Direccion de Envío: ${orden.idOrden.idEnvio.direccion}</p>
                <p>Modalidad Entrega de Envío: ${orden.idOrden.idEnvio.modalidadEntrega}</p>
                <p>Telefono de Envío: ${orden.idOrden.idEnvio.telefono}</p>

                <p>Cédula de Usuario: ${orden.idOrden.cedula.cedula}</p>
                <p>Nombre de Usuario: ${orden.idOrden.cedula.nombre}</p>

                <p>Nombre de Producto: ${orden.idProducto.nombre}</p>
                <p>Precio de Producto: ${orden.idProducto.precio}</p>
                <img src="${orden.idProducto.image_Url}" alt="Descripción de la imagen"> */