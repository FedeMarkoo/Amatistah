<html>
<script src="https://secure.mlstatic.com/sdk/javascript/v1/mercadopago.js"></script>
<script src = "https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<body>
<form action="https://www.mercadopago.com/mla/buybutton" method="post" target="MercadoPago">

<br />

<input alt="Comprar Ahora" src="https://www.mercadopago.com/org-img/MP3/buy_now.gif" type="image" />

<input name="acc_id" type="hidden" value="xxxxxxx" />

<input name="url_cancel" type="hidden" value="http://www.mercadopago.com" />

<input name="item_id" type="hidden" value="1234" />

<input name="name" type="hidden" value="Trabajos generales" />

<input name="currency" type="hidden" value="ARG" />

<input name="price" type="text" value="2" />

<input name="url_process" type="hidden" value="http://www.mercadopago.com" />

<input name="url_succesfull" type="hidden" value="http://www.herreriafullmet.blogspot.com" />

<input name="shipping_cost" type="hidden" value="" />

<input name="enc" type="hidden" value="xxxxxxxxxxxxxxxxxxxx " />

<input name="ship_cost_mode" type="hidden" value="" />

<input name="op_retira" type="hidden" value="O" />

<input name="extra_part" type="hidden" value="www.herreriafullmet.blogspot.com" />

 </form>
</body>
</html>

<script type="text/javascript">
window.Mercadopago.setPublishableKey("TEST-2eda559a-a0aa-4699-a537-ee1ce7d14fcc");

document.getElementById('cardNumber').addEventListener('keyup', guessPaymentMethod);
document.getElementById('cardNumber').addEventListener('change', guessPaymentMethod);

function guessPaymentMethod(event) {
    let cardnumber = document.getElementById("cardNumber").value;

    if (cardnumber.length >= 6) {
        let bin = cardnumber.substring(0,6);
        window.Mercadopago.getPaymentMethod({
            "bin": bin
        }, setPaymentMethod);
    }
};

function setPaymentMethod(status, response) {
    if (status == 200) {
        let paymentMethodId = response[0].id;
        let element = document.getElementById('payment_method_id');
        element.value = paymentMethodId;
        getInstallments();
    } else {
        alert('payment method info error: ${response}');
    }
}

function getInstallments(){
    window.Mercadopago.getInstallments({
        "payment_method_id": document.getElementById('payment_method_id').value,
        "amount": parseFloat(document.getElementById('transaction_amount').value)

    }, function (status, response) {
        if (status == 200) {
            document.getElementById('installments').options.length = 0;
            response[0].payer_costs.forEach( installment => {
                let opt = document.createElement('option');
                opt.text = installment.recommended_message;
                opt.value = installment.installments;
                document.getElementById('installments').appendChild(opt);
            });
        } else {
            alert('installments method info error: ${response}');
        }
    });
}

doSubmit = false;
document.querySelector('#pay').addEventListener('submit', doPay);

function doPay(event){
    event.preventDefault();
    if(!doSubmit){
        var $form = document.querySelector('#pay');

        window.Mercadopago.createToken($form, sdkResponseHandler);

        return false;
    }
};

function sdkResponseHandler(status, response) {
    if (status != 200 && status != 201) {
        alert("verify filled data");
    }else{
        var form = document.querySelector('#pay');
        var card = document.createElement('input');
        card.setAttribute('name', 'token');
        card.setAttribute('type', 'hidden');
        card.setAttribute('value', response.id);
        form.appendChild(card);
        doSubmit=true;
        form.submit();
    }
};

$(document).ready(function() {
	window.Mercadopago.getIdentificationTypes();
 });
</script>