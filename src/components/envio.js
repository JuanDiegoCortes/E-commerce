

document.addEventListener('DOMContentLoaded', (event) => {
    let solicitudEnviada=false;

    let ciudadSeleccionadaId = "";
    let modalidadEntregaSeleccionada = "";

    document.querySelectorAll('.boton-ciudad').forEach(button => {
        button.addEventListener('click', function() {
            ciudadSeleccionadaId = parseInt(this.id);
        });
    });

    document.querySelectorAll('.boton-modalidadEntrega').forEach(button => {
        button.addEventListener('click', function() {
            modalidadEntregaSeleccionada = this.id;
        });
    });

    let botonPago = document.getElementById("botonPago");
    botonPago.addEventListener("click", capturarValores);

    function capturarValores() {
        let nombre = document.getElementById('nombre').value;
        let apellido = document.getElementById('apellido').value;
        let telefono = document.getElementById('numTelefono').value;
        let direccion = document.getElementById('direccion').value;
        let codigoPostal = document.getElementById('codigoPostal').value;
        let referencias = document.getElementById('referencias').value;

        if (nombre === "" || apellido === "" || codigoPostal === "" || ciudadSeleccionadaId === "" || modalidadEntregaSeleccionada === "" || telefono === "" || direccion === "") {
            alert('Por favor, complete todos los campos.');
            return "";
        } else {
            const envioData = {
                nombre: nombre,
                apellido: apellido,
                direccion: direccion,
                modalidadEntrega: modalidadEntregaSeleccionada,
                telefono: telefono,
                idCiudad: {
                    idCiudad: ciudadSeleccionadaId
                },
                codigoPostal: codigoPostal,
                referencias: referencias,
            };
            console.log(envioData);
            // enviarDatos(envioData);
        }
    }
});

// function enviarDatos(data){
//     fetch("http://localhost:8081/Apiweb/v1/envio/", {
//         method: 'POST',
//         headers: {
//         'Content-Type': 'application/json'
//         },
//         body: JSON.stringify(data)
//     })
//     .then(response => {
//         if (response.ok) {
//             return response.text().then(text => {
//                 try {
//                     return JSON.parse(text);
//                 } catch {
//                     alert('Datos enviados correctamente'); // pero la respuesta no es JSON.
//                     return console.log(text);
//                 }
//             });
//         } else {
//             alert('Error al enviar los datos.');
//         }
//     })
//     .catch(error => {
//         console.error('Error en la solicitud Fetch:', error);
//     });
// }


