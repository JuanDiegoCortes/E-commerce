window.onload = function() {
    let idCategoria = JSON.parse(sessionStorage.getItem('categoriaId'));

    const btnActualizar = document.querySelector('.botonActualizar');
    btnActualizar.addEventListener('click', actualizarCategoria);

    function actualizarCategoria() {
        let nombre = document.getElementById('nombre').value;
        let descripcion = document.getElementById('descripcion').value;

        let detallesCategoria = {
            nombre: nombre,
            descripcion: descripcion
        };

        fetch(`http://localhost:8081/Apiweb/v1/categoria/${idCategoria}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(detallesCategoria)
        }).then(function(response) {
            if (response.text().then(text => {
                Toastify({
                    text: text,
                    duration: 2000,
                    close: true,
                    gravity: "top",
                    position: "right",
                    stopOnFocus: true,
                    style: {
                      background: "linear-gradient(to right, #4b33a8, #785ce9)",
                      borderRadius: "2rem",
                      textTransform: "uppercase",
                      fontSize: ".75rem"
                    },
                    offset: {
                      x: '1.5rem',
                      y: '1.5rem'
                    },
                    onClick: function(){}
                  }).showToast();
            })) {
            }
        });
    }
}