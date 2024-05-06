document.addEventListener('DOMContentLoaded', (event) => {
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
            Toastify({
                text: "Por favor, complete todos los campos.",
                duration: 1000,
                close: true,
                gravity: "top",
                position: "right",
                stopOnFocus: true,
                style: {
                    background: "linear-gradient(to right, #4b33a8, #785ce9)",
                    borderRadius: "2rem",
                }
            }).showToast();
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
            enviarDatos(envioData);
        }
    }

    function enviarDatos(data){
        const url = `http://localhost:8081/Apiweb/v1/envio/`;
        fetch(url, {
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
                        Toastify({
                            text: "Informacion enviada correctamente",
                            duration: 1000,
                            close: true,
                            gravity: "top",
                            position: "right",
                            stopOnFocus: true,
                            style: {
                                background: "linear-gradient(to right, #4b33a8, #785ce9)",
                                borderRadius: "2rem",
                            }
                        }).showToast();
                        setTimeout(() => {
                            window.location.href = "../pages/index.html";
                        }, 2000);
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

});
