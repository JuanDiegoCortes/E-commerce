// JavaScript para obtener y mostrar la información del producto
window.onload = function() {
    // Obtener el producto seleccionado almacenado en sessionStorage
    const productoSeleccionado = JSON.parse(sessionStorage.getItem("productoSeleccionado"));

    // Mostrar la información del producto en la página
    if (productoSeleccionado) {
        document.getElementById("categoriaProducto").innerText +=`\t ${productoSeleccionado.idCategoria.nombre}`;
        document.getElementById("generoProducto").innerText += `\t ${productoSeleccionado.genero}`;
        document.getElementById("nombreProducto").innerText = productoSeleccionado.nombre;
        document.getElementById("descripcionProducto").innerText += `\t ${productoSeleccionado.descripcion}`;
        document.getElementById("imagenProducto").src = productoSeleccionado.image_Url;
        document.getElementById("precioProducto").innerText += productoSeleccionado.precio;
        // Puedes continuar mostrando el resto de la información del producto
    } else {
        console.error("No se encontró ningún producto seleccionado.");
    }
};