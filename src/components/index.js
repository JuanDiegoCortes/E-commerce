fetch('/api/productos')
  .then(response => response.json())
  .then(datos => {
    const productGrid = document.querySelector('.product-grid');
    datos.forEach(producto => {
      const productoElement = document.createElement('div');
      productoElement.classList.add('producto');
      productoElement.innerHTML = `
        <img src="${producto.imagen}" alt="${producto.nombre}">
        <h3>${producto.nombre}</h3>
        <p>${producto.descripcion}</p>
        <p>Precio: ${producto.precio}</p>
      `;
      productGrid.appendChild(productoElement);
    });
  })
  .catch(error => console.error('Error:', error));