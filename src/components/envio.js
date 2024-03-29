
// Obtener referencias a los elementos del formulario
const nombreInput = document.getElementById('nombre');
const apellidoInput = document.getElementById('apellido');
const telefonoInput = document.getElementById('numTelefono');
const direccionInput = document.getElementById('direccion');
const referenciasInput = document.getElementById('referencias');
const codigoPostalInput = document.getElementById('codigoPostal');
const ciudadDropdown = document.getElementById('boton-ciudades');
document.getElementById("botonPago").addEventListener("click",xd)
function xd(){
    const nombre = "juan";
    console.log('Nombre:', nombre);
    const apellido = apellidoInput.value;
    const telefono = telefonoInput.value;
    const direccion = direccionInput.value;
    const referencias = referenciasInput.value;
    const codigoPostal = codigoPostalInput.value;
    const ciudadSeleccionada = ciudadDropdown.innerText;

    console.log('Apellido:', apellido);
    console.log('Teléfono:', telefono);
    console.log('Dirección:', direccion);
    console.log('Referencias:', referencias);
    console.log('Código postal:', codigoPostal);
    console.log('Ciudad seleccionada:', ciudadSeleccionada);

}


