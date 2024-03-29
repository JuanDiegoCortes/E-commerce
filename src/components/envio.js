document.getElementById("botonPago").addEventListener("click", xd);

function xd() {
    const nombre = document.getElementById('nombre').value;
    const apellido = document.getElementById('apellido').value;
    const telefono = document.getElementById('numTelefono').value;
    const direccion = document.getElementById('direccion').value;
    const referencias = document.getElementById('referencias').value;
    const codigoPostal = document.getElementById('codigoPostal').value;
    const ciudadSeleccionada = document.querySelector('.boton-ciudades').innerText.trim();

    console.log('Nombre:', nombre);
    console.log('Apellido:', apellido);
    console.log('Teléfono:', telefono);
    console.log('Dirección:', direccion);
    console.log('Referencias:', referencias);
    console.log('Código postal:', codigoPostal);
    console.log('Ciudad seleccionada:', ciudadSeleccionada);
}
