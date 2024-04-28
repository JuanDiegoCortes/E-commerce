window.onload = function() {
    const disenoPSeleccionado = JSON.parse(sessionStorage.getItem("disenoPSeleccionado"));
    const idDisenoP = disenoPSeleccionado.idDisenoP;

    console.log(disenoPSeleccionado);
    if (disenoPSeleccionado) {
        document.getElementById("nombreDisenoP").innerText += disenoPSeleccionado.cedula.nombre;
        document.getElementById("correoDisenoP").innerText += disenoPSeleccionado.cedula.correo;
        document.getElementById("cedulaDisenoP").innerText += disenoPSeleccionado.cedula.cedula;
    }
}