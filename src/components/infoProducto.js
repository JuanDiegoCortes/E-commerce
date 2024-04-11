window.onload = function() {
    const productoSeleccionado = JSON.parse(sessionStorage.getItem("productoSeleccionado"));
    
    if (productoSeleccionado) {
        document.getElementById("categoriaProducto").innerText +=`\t ${productoSeleccionado.idCategoria.nombre}`;
        document.getElementById("generoProducto").innerText += `\t ${productoSeleccionado.genero}`;
        document.getElementById("nombreProducto").innerText = productoSeleccionado.nombre;
        document.getElementById("descripcionProducto").innerText += `\t ${productoSeleccionado.descripcion}`;
        document.getElementById("imagenProducto").src = productoSeleccionado.image_Url;
        document.getElementById("precioProducto").innerText += productoSeleccionado.precio;
    } else {
        console.error("No se encontró ningún producto seleccionado.");
    }
};