document.addEventListener('DOMContentLoaded', () => {
    const cartItemsDiv = document.getElementById('cart-items');
    const totalValueElement = document.getElementById('totalValue');
    const totalPixElement = document.getElementById('TotalCalculoPix');
    const totalCartaoElement = document.getElementById('TotalCalculoCartao');
    const divPix = document.querySelector('#divPix');
    const cartaoCredito = document.querySelector('#cartaoCredito');
    const finalizarCompraBtnPix = document.getElementById('finalizarCompraBtnPix');
    const finalizarCompraBtnCartao = document.getElementById('finalizarCompraBtnCartao');

    // Inicialmente oculta as seções Pix e Cartão
    divPix.style.display = 'none';
    cartaoCredito.style.display = 'none';

    // Alterna entre as opções de pagamento
    const radioButtons = document.querySelectorAll('input[name="opcoes-pagamento"]');
    radioButtons.forEach(radio => {
        radio.addEventListener('change', () => {
            if (radio.value === 'pix') {
                divPix.style.display = 'block';
                cartaoCredito.style.display = 'none';
            } else if (radio.value === 'cartao') {
                divPix.style.display = 'none';
                cartaoCredito.style.display = 'block';
            }
        });
    });
});

// Função para validar o formulário do Pix
function validarPix() {
    const cpf = document.getElementById('cpf').value.trim();
    if (cpf === '') {
        alert('Por favor, preencha o campo CPF.');
        return false;
    }
    return true;
}

// Função para validar o formulário do cartão de crédito
function validarCartao() {
    const numero = document.getElementById('numeroCartao').value.trim();
    const nome = document.getElementById('titularCartao').value.trim();
    const csv = document.getElementById('csv').value.trim();
    const vencimento = document.getElementById('vencimento').value.trim();

    if (numero === '') {
        alert('Por favor, preencha o número do cartão.');
        return false;
    }

    if (nome === '') {
        alert('Por favor, preencha o nome do titular.');
        return false;
    }

    if (csv === '') {
        alert('Por favor, preencha o código de segurança.');
        return false;
    }

    if (vencimento === '') {
        alert('Por favor, preencha a data de vencimento.');
        return false;
    }

    return true;
}

// Função para finalizar a compra
function finalizarCompra(metodo, validacao) {
    if (!validacao()) {
        return;
    }

    const cartItems = document.querySelectorAll('.cart-item');
    if (cartItems.length === 0) {
        alert('Seu carrinho está vazio!');
        return;
    }

    fetch('/finalizar-venda', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            metodo: metodo,
            cart: Array.from(cartItems).map(item => ({
                    idProduto: item.getAttribute('data-id'),
                    quantidade: item.querySelector('.quantidade').textContent,
                })),
        }),
    })
            .then(response => {
                if (response.ok) {
                    alert(`Compra finalizada via ${metodo}!`);
                    window.location.href = '/home-loja';
                } else {
                    alert('Erro ao finalizar a compra.');
                }
            })
            .catch(error => {
                console.error('Erro de conexão:', error);
            });
}

// Event listeners para os botões de finalização
document.getElementById('finalizarVendaPix')?.addEventListener('click', () => {
    finalizarCompra('Pix', validarPix);
});

document.getElementById('finalizarVendaCartao')?.addEventListener('click', () => {
    finalizarCompra('Cartão', validarCartao);
});

// Atualiza o total do carrinho
function updateCartTotal() {
    const total = Array.from(document.querySelectorAll('.cart-item')).reduce((sum, item) => {
        const preco = parseFloat(item.querySelector('.preco').textContent.replace(',', '.'));
        const quantidade = parseInt(item.querySelector('.quantidade').textContent, 10);
        return sum + preco * quantidade;
    }, 0);

    document.getElementById('cartTotal').textContent = `Total: R$ ${total.toFixed(2).replace('.', ',')}`;
}

// Manipulação de ícones e validação do número do cartão
$(document).ready(function () {
    $('#card1').hide();
    $('#card2').hide();
    $('#cartaoInvalido').hide();

    $('#numeroCartao').on('input', function () {
        const cardNumber = $(this).val();
        const cardIcon1 = $('#card1');
        const cardIcon2 = $('#card2');
        const cardMessage = $('#cartaoInvalido');

        cardIcon1.hide();
        cardIcon2.hide();
        cardMessage.hide();

        if (cardNumber.startsWith("1234")) {
            cardIcon1.show();
        } else if (cardNumber.startsWith("4321")) {
            cardIcon2.show();
        } else if (cardNumber.length >= 4) {
            cardMessage.show();
        }
    });
});
