window.onload = function() {
    const disenoPSeleccionado = JSON.parse(sessionStorage.getItem("disenoPSeleccionado"));
    const idDisenoP = disenoPSeleccionado.idDisenoP;
    
    console.log(disenoPSeleccionado);
    if (disenoPSeleccionado) {
        document.getElementById("nombreDisenoP").innerText += disenoPSeleccionado.cedula.nombre;
        document.getElementById("correoDisenoP").innerText += disenoPSeleccionado.cedula.correo;
        document.getElementById("cedulaDisenoP").innerText += disenoPSeleccionado.cedula.cedula;
        document.getElementById("imagenDisenoP").src = disenoPSeleccionado.image_url;
        document.getElementById("estadoDisenoP").innerText += disenoPSeleccionado.idOrdenProd.idOrden.estado;
        document.getElementById("imagenProducto").src = disenoPSeleccionado.idOrdenProd.idProducto.image_Url;
        
    }
}