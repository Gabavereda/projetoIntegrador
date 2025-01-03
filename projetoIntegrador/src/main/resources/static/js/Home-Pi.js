document.addEventListener('DOMContentLoaded', function () {
    const cards = document.querySelectorAll('.singleCard');
    cards.forEach(card => {
        const decreaseBtn = card.querySelector('.decreaseBtn');
        const increaseBtn = card.querySelector('.increaseBtn');
        const quantityInput = card.querySelector('.quantityInput');
        const totalValue = card.querySelector('.totalValue');
        const produtoPreco = parseFloat(
                card.querySelector('#produtoPreco').textContent
                .replace('R$ ', '')
                .replace(',', '.')
                );

        let quantity = parseInt(quantityInput.value);

        function updateTotal() {
            const total = (quantity * produtoPreco).toFixed(2);
            totalValue.textContent = `R$ ${total}`;
            // Atualizar o valor do campo de quantidade para enviar corretamente no formulário
            quantityInput.value = quantity;
        }

        increaseBtn.addEventListener('click', () => {
            quantity++;
            updateTotal();
        });

        decreaseBtn.addEventListener('click', () => {
            if (quantity > 1) {
                quantity--;
                updateTotal();
            }
        });

        quantityInput.addEventListener('input', () => {
            const newValue = parseInt(quantityInput.value);
            if (!isNaN(newValue) && newValue > 0) {
                quantity = newValue;
                updateTotal();
            } else {
                quantityInput.value = quantity;
            }
        });

        updateTotal();
    });
});

