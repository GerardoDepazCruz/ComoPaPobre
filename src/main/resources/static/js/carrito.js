document.addEventListener('DOMContentLoaded', () => {
    let cart = JSON.parse(localStorage.getItem('cart')) || [];

    const updateCart = () => {
        const cartItems = document.getElementById('cartItems');
        const cartTotal = document.getElementById('cartTotal');
        const cartCount = document.getElementById('cartCount');

        cartItems.innerHTML = '';
        let total = 0;

        cart.forEach((item, index) => {
            total += item.price * item.quantity;

            const itemHTML = `
                <div class="cart-item d-flex align-items-center mb-2">
                    <img src="${item.image}" alt="${item.name}" 
                         class="me-2" style="width: 50px; height: 50px; object-fit: cover;">
                    <div class="flex-grow-1">
                        <div class="fw-bold">${item.name}</div>
                        <div class="d-flex align-items-center">
                            <button class="btn btn-sm btn-outline-warning" 
                                    onclick="updateQuantity(${index}, ${item.quantity - 1}, event)">-</button>
                            <span class="mx-2">${item.quantity}</span>
                            <button class="btn btn-sm btn-outline-warning" 
                                    onclick="updateQuantity(${index}, ${item.quantity + 1}, event)">+</button>
                            <button class="btn btn-danger btn-sm ms-2" 
                                    onclick="removeItem(${index}, event)">X</button>
                            <span class="ms-2">S/ ${(item.price * item.quantity).toFixed(2)}</span>
                        </div>
                    </div>
                </div>
            `;
            cartItems.innerHTML += itemHTML;
        });

        cartTotal.textContent = total.toFixed(2);
        cartCount.textContent = cart.reduce((sum, item) => sum + item.quantity, 0);
        localStorage.setItem('cart', JSON.stringify(cart));
    };

    window.removeItem = (index, event) => {
        event.stopPropagation();
        cart.splice(index, 1);
        updateCart();
    };

    window.updateQuantity = (index, newQuantity, event) => {
        event.stopPropagation();
        if (newQuantity > 0) {
            cart[index].quantity = newQuantity;
        } else {
            cart.splice(index, 1);
        }
        updateCart();
    };

    const handleAddToCart = (button) => {
        const card = button.closest('.promo-card, .prod-card');
        const priceElement = card.querySelector('.discounted-price, .selected-price');
        const price = parseFloat(priceElement.textContent.replace('S/ ', '').trim());

        const product = {
            id: Date.now(),
            name: card.querySelector('h3').textContent.trim(),
            price: price,
            image: card.querySelector('img').src,
            quantity: 1
        };

        const existingItem = cart.find(item => item.name === product.name && item.price === product.price);
        existingItem ? existingItem.quantity++ : cart.push(product);

        updateCart();
    };

    document.querySelectorAll('.btn-promo, .btn-prod').forEach(button => {
        button.addEventListener('click', (e) => {
            e.preventDefault();
            handleAddToCart(button);
        });
    });



    //Para realizar el pedido del carrito
    window.checkout = (event) => {
        event.stopPropagation();
        console.log("Checkout invocado. isAuthenticated =", isAuthenticated);

        // Verificar si el usuario está autenticado
        if (!isAuthenticated) {
            alert('Ingrese sesión primero, por favor');
            return;
        }

        
        if (cart.length === 0) {
            alert('¡Tu carrito está vacío!');
            return;
        }

        const total = document.getElementById('cartTotal').textContent;
        const confirmacion = confirm(`¿Confirmar pedido por S/ ${total}?`);

        if (confirmacion) {
            // Vaciar el carrito
            cart = [];

            // Actualizar el localStorage para reflejar el carrito vacío
            localStorage.setItem('cart', JSON.stringify(cart));

            // Actualizar la vista del carrito 
            updateCart();

            // Mostrar mensaje de éxito
            alert('Pedido realizado con éxito');
        }
    };



    // Cerrar carrito solo con botón específico
    document.querySelector('.cart-dropdown .btn-close').addEventListener('click', () => {
        document.querySelector('.dropdown-menu').classList.remove('show');
    });

    updateCart();
});