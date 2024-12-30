// Lista de produtos
const products = [
    { id: 1, name: 'Produto 1', price: 100 },
    { id: 2, name: 'Produto 2', price: 200 },
    { id: 3, name: 'Produto 3', price: 300 },
];

// Função para inicializar os eventos de clique
document.addEventListener('DOMContentLoaded', () => {
    const addToCartButtons = document.querySelectorAll('.addToCartBtn');

    addToCartButtons.forEach(button => {
        button.addEventListener('click', event => {
            // Encontra o card do produto correspondente
            const productCard = button.closest('.singleCard');

            // Captura os dados do produto
            const productName = productCard.querySelector('.card-title').innerText;
            const productDescription = productCard.querySelector('.card-text').innerText;
            const productPrice = productCard.querySelector('#valorTotalProduto').innerText;
            const productQuantity = productCard.querySelector('.quantityInput').value;
            const produtoImage = productCard.querySelector("#produtoImage").src;


            // Adiciona ao carrinho
            addToCart({
                name: productName,
                description: productDescription,
                price: productPrice,
                quantity: parseInt(productQuantity),
                fotoProduto: produtoImage
            });

            alert(`"${productName}" foi adicionado ao carrinho!`);
        });
    });
});


// Função para adicionar ao carrinho usando localStorage
function addToCart(product) {
    const cart = JSON.parse(localStorage.getItem('cart')) || [];
    const existingProductIndex = cart.findIndex(item => item.name === product.name);

    if (existingProductIndex > -1) {
        // Atualiza a quantidade do produto no carrinho
        cart[existingProductIndex].quantity += product.quantity;
    } else {
        // Adiciona novo produto
        cart.push(product);
    }

    // Atualiza o localStorage
    localStorage.setItem('cart', JSON.stringify(cart));

    // Salve o último produto selecionado no localStorage
    localStorage.setItem('selectedProduct', JSON.stringify(product));

}
// para ver o carrinho
console.log('Carrinho:', JSON.parse(localStorage.getItem('cart')));

const cards = document.querySelectorAll('.singleCard');

cards.forEach(card => {
    // Seleciona os elementos específicos dentro de cada card
    const decreaseBtn = card.querySelector('.decreaseBtn');
    const increaseBtn = card.querySelector('.increaseBtn');
    const quantityInput = card.querySelector('.quantityInput');
    const totalValue = card.querySelector('.totalValue');
    const produtoPreco = parseFloat(
        card.querySelector('#produtoPreco').textContent
            .replace('R$ ', '') // Remove o prefixo "R$ "
            .replace(',', '.') // Substitui vírgula por ponto para o formato numérico
    );

    // Variáveis de controle
    let quantity = parseInt(quantityInput.value);

    // Função para atualizar o total
    function updateTotal() {
        const total = (quantity * produtoPreco).toFixed(2); // Preço fixo por produto
        totalValue.textContent = `R$ ${total}`; // Atualiza o total exibido
    }

    // Aumenta a quantidade
    increaseBtn.addEventListener('click', () => {
        quantity++;
        quantityInput.value = quantity;
        updateTotal();
    });

    // Diminui a quantidade
    decreaseBtn.addEventListener('click', () => {
        if (quantity > 1) {
            quantity--;
            quantityInput.value = quantity;
            updateTotal();
        }
    });

    // Atualiza a quantidade ao alterar o campo manualmente
    quantityInput.addEventListener('input', () => {
        const newValue = parseInt(quantityInput.value);
        if (!isNaN(newValue) && newValue > 0) {
            quantity = newValue;
            updateTotal();
        } else {
            quantityInput.value = quantity;
        }
    });

    // Inicializa o total na carga inicial
    updateTotal();
});
