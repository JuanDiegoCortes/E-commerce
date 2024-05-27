window.onload = function() {
    let producto = JSON.parse(sessionStorage.getItem('productoEditar'));
    console.log(producto);
    
    for (let i = 1; i <= 6; i++) {
        if (producto.idTalla.idTalla !== i) {
            let tallaElements = document.querySelectorAll(`#talla_${i}`);
            tallaElements.forEach(element => {
                element.style.display = "none";
            });
        }
    }

    const btnActualizar = document.querySelector('.botonActualizar');
    btnActualizar.addEventListener('click', actualizarProducto);

    let categoriaSeleccionada;
    let generoSeleccionado;
    let personalizableSeleccionado;
    let estadoSeleccionado;

    document.querySelectorAll('.dropdown-item.boton-categoria').forEach(item => {
        item.addEventListener('click', function() {
            categoriaSeleccionada = this.id;
        });
    });

    document.querySelectorAll('.dropdown-item.boton-genero').forEach(item => {
        item.addEventListener('click', function() {
            generoSeleccionado = this.id;
        });
    });

    document.querySelectorAll('.dropdown-item.boton-personalizable').forEach(item => {
        item.addEventListener('click', function() {
            personalizableSeleccionado = this.id;
        });
    });

    document.querySelectorAll('.dropdown-item.boton-estado').forEach(item => {
        item.addEventListener('click', function() {
            estadoSeleccionado = this.id;
        });
    });

    function actualizarProducto() {
        let nombre = document.getElementById('nombre').value;
        let descripcion = document.getElementById('descripcion').value;
        let precio = document.getElementById('precio').value;
        let cantidadAsignada = document.getElementById('cantidad').value;
    
        let productoDetalles = {
            cantidad: cantidadAsignada,
            idProducto: {
                idProducto: producto.idProducto.idProducto,
                nombre: nombre,
                descripcion: descripcion,
                genero: generoSeleccionado,
                precio: precio,
                estado: estadoSeleccionado,
                personalizable: personalizableSeleccionado,
                categoria: categoriaSeleccionada
            }
        };

        fetch(`http://localhost:8081/Apiweb/v1/prodTalla/${producto.idProducto.idProducto}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(productoDetalles)
        }).then(response => response.text().then(text => {
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
        }));
        
    }
}