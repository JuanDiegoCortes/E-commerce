let ciudadSeleccionadaId = "";
let modalidadEntregaSeleccionada = "";
// let nombre = "";
// let apellido = "";
let telefono = "";
let direccion = "";
// let codigoPostal = "";
// let referencias = "";

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

document.getElementById("botonPago").addEventListener("click", capturarValores);

function capturarValores() {

    // nombre = document.getElementById('nombre').value;
    // apellido = document.getElementById('apellido').value;
    telefono = document.getElementById('numTelefono').value;
    direccion = document.getElementById('direccion').value;
    // codigoPostal = document.getElementById('codigoPostal').value;
    // referencias = document.getElementById('referencias').value;

    if (ciudadSeleccionadaId === "" || modalidadEntregaSeleccionada === "" || telefono === "" || direccion === "") {
        alert('Por favor, complete todos los campos.');
        return console.log(ciudadSeleccionadaId, modalidadEntregaSeleccionada, telefono, direccion)
    }else {
        const envioData = {

            // apellido: apellido,
            direccion: direccion,
            modalidadEntrega: modalidadEntregaSeleccionada,
            telefono: telefono,
            idCiudad: {
                idCiudad: ciudadSeleccionadaId
            }
            // nombre: nombre
            // codigoPostal: codigoPostal,
            // referencias: referencias,
        };
        console.log(envioData);
        enviarDatos(envioData);
    }
}

function enviarDatos(data){
    fetch("http://localhost:8081/Apiweb/v1/envio/", {
        method: 'POST',
        headers: {
        'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (response.ok) {
            return response.text().then(text => {
                try {
                    return JSON.parse(text);
                } catch {
                    alert('Datos enviados correctamente'); // pero la respuesta no es JSON.
                    return console.log(text);
                }
            });
        } else {
            alert('Error al enviar los datos.');
        }
    })
    .catch(error => {
        console.error('Error en la solicitud Fetch:', error);
    });
}


