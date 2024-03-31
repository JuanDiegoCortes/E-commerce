let ordenes = [];
let cedula = 582347196;

function fetchData() {
    return fetch("http://localhost:8081/Apiweb/v1/orden/")
        .then(response => response.json())
        .then(data => {
            ordenes = data;
            cargarOrdenes(ordenes, cedula);
        })
        .catch(error => {
            console.error("Error al obtener los datos:", error);
        });
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