/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function mascaraInteiro() {
    if (event.keyCode < 48 || event.keyCode > 57) {
        event.returnValue = false;
        return false;
    }
    return true;
}

function MascaraCNPJ(cnpj) {
    if (mascaraInteiro(cnpj) === false) {
        event.returnValue = false;
    }
    return formataCampo(cnpj, '00.000.000/0000-00', event);
}

function MascaraCPF(cpf) {
    if (mascaraInteiro(cpf) === false) {
        event.returnValue = false;
    }
    return formataCampo(cpf, '000.000.000-00', event);
}
function formataCampo(campo, Mascara, evento) {
    var boleanoMascara;

    var Digitato = evento.keyCode;
    exp = /\-|\.|\/|\(|\)| /g;
    campoSoNumeros = campo.value.toString().replace(exp, "");

    var posicaoCampo = 0;
    var NovoValorCampo = "";
    var TamanhoMascara = campoSoNumeros.length;
    ;

    if (Digitato !== 8) { // backspace 
        for (i = 0; i <= TamanhoMascara; i++) {
            boleanoMascara = ((Mascara.charAt(i) === "-") || (Mascara.charAt(i) === ".")
                    || (Mascara.charAt(i) === "/"));
            boleanoMascara = boleanoMascara || ((Mascara.charAt(i) === "(")
                    || (Mascara.charAt(i) === ")") || (Mascara.charAt(i) === " "));
            if (boleanoMascara) {
                NovoValorCampo += Mascara.charAt(i);
                TamanhoMascara++;
            } else {
                NovoValorCampo += campoSoNumeros.charAt(posicaoCampo);
                posicaoCampo++;
            }
        }
        campo.value = NovoValorCampo;
        return true;
    } else {
        return true;
    }
}
