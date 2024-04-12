window.onload = function() {
    const ordenSeleccionada = JSON.parse(sessionStorage.getItem("ordenSeleccionada"));
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
}
