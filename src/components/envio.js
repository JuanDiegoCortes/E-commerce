let ciudadSeleccionadaId = "";
let modalidadEntregaSeleccionada = "";

document.querySelectorAll('.boton-ciudad').forEach(button => { //necesito
    button.addEventListener('click', function() {
        ciudadSeleccionadaId = this.id;
    });
});

document.querySelectorAll('.boton-modalidadEntrega').forEach(button => { //necesito
    button.addEventListener('click', function() {
        modalidadEntregaSeleccionada = this.id;
    });
});

document.getElementById("botonPago").addEventListener("click", capturarValores);

function capturarValores() {
    const nombre = document.getElementById('nombre').value; 
    const apellido = document.getElementById('apellido').value;
    const telefono = document.getElementById('numTelefono').value; //necesito
    const direccion = document.getElementById('direccion').value; //necesito
    const codigoPostal = document.getElementById('codigoPostal').value;
    const referencias = document.getElementById('referencias').value;

    console.log('Nombre:', nombre);
    console.log('Apellido:', apellido);
    console.log('Teléfono:', telefono);
    console.log('Dirección:', direccion);
    console.log('Referencias:', referencias);
    console.log('Codigo postal:', codigoPostal);
    console.log('Texto del botón de modalidad entrega:', modalidadEntregaSeleccionada);
    console.log('ID del botón de ciudad seleccionada:', ciudadSeleccionadaId);
}


// document.getElementById("botonPago").addEventListener("click", function() {
//     const nombre = document.getElementById('nombre').value;
//     const apellido = document.getElementById('apellido').value;
//     const telefono = document.getElementById('numTelefono').value;
//     const direccion = document.getElementById('direccion').value;
//     // Captura el resto de los campos del formulario

//     const envioData = {
//         nombre: nombre,
//         apellido: apellido,
//         telefono: telefono,
//         direccion: direccion,
//         // Agrega el resto de los campos
//     };

//     fetch('/Apiweb/v1/envio/', {
//         method: 'POST',
//         headers: {
//             'Content-Type': 'application/json'
//         },
//         body: JSON.stringify(envioData)
//     })
//     .then(response => {
//         if (response.ok) {
//             console.log('Datos enviados correctamente');
//             // Puedes mostrar un mensaje de éxito aquí si lo deseas
//         } else {
//             console.error('Error al enviar datos');
//             // Puedes mostrar un mensaje de error aquí si lo deseas
//         }
//     })
//     .catch(error => {
//         console.error('Error:', error);
//         // Puedes mostrar un mensaje de error aquí si lo deseas
//     });
// });


