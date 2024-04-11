window.onload = function() {
    const ordenSeleccionada = JSON.parse(sessionStorage.getItem("ordenSeleccionada"));
    console.log(ordenSeleccionada);
    if(ordenSeleccionada){
        document.getElementById("nombreOrden").innerText +=`\t ${ordenSeleccionada.cedula.nombre}`;
        // document.getElementById("correoOrden").innerText += ordenSeleccionada.cedula.correo;
        // document.getElementById("cedulaOrden").innerText += ordenSeleccionada.cedula.cedula;
    }else{
        console.error("No se encontró ninguna orden seleccionada.");
    }
}
