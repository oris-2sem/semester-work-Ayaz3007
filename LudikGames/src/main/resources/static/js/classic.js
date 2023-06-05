let arraySymbols = [];

window.addEventListener('load', async function() {

    let less = document.getElementById("less");
    let more = document.getElementById("more");
    less.addEventListener('click', decreaseStake);
    more.addEventListener('click', increaseStake);

    await fetch('/get-images', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-Token': document.getElementsByName("_csrf")[0].value
        }})
        .then(response => response.json())
        .then(data => {
            data.forEach(item => {
                arraySymbols.push(item)
            });
        })
        .catch(error => {
            console.error('Ошибка:', error);
        });


    document.getElementById("button-slot").addEventListener("click", game);
    let divs = document.getElementsByClassName("symbol");
    for(let i = 0; i < divs.length; i++) {
        let numberRandom = generaRandom(arraySymbols.length);
        let img = document.createElement('img');
        img.src = arraySymbols[numberRandom]
        divs[i].replaceChildren(img);
    }
    document.getElementById("stake").innerHTML = (10).toString()
    let balance = Number(document.getElementById("balance").innerHTML.substring(9))
    if(balance < currentStake) {
        document.getElementById("button-slot").disabled = true;
    }
    document.getElementById("less").disabled = true
});

async function game(){
    let balance = Number(document.getElementById("balance").innerHTML.substring(9))
    if(balance < currentStake) {
        document.getElementById("button-slot").disabled = true;
        return null;
    }
    let slots = document.getElementsByClassName("symbol")
    for(let slot of slots) {
        slot.style.background = "#ffffff";
    }
    document.getElementById("button-slot").disabled = true;
    let isSpinSuccess = true;
    let id = getId(window.location.pathname)
    fetch("/games/slots/" + id + "/spin", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-Token': document.getElementsByName("_csrf")[0].value
        },
        body: JSON.stringify({'stake': currentStake})
    }).then(response => response.json()).then(spinResult => {
        isSpinSuccess = spinResult.result === 'Success';
        document.getElementById("balance").innerHTML = 'Balance: ' + spinResult.balance
    });

    if(!isSpinSuccess) {
        document.getElementById("button-slot").disabled = false;
        return null;
    }


    const attempts = 10;
    let result = [];

    for(let i  = 0; i < slots.length; i++) {
        let c = 0;
        let spin = setInterval(function () {
            let numberRandom = generaRandom(arraySymbols.length);
            let img = document.createElement('img');
            img.src = arraySymbols[numberRandom]
            slots[i].replaceChildren(img);
            console.log(arraySymbols[numberRandom])
            c++;
            if (c === attempts) {
                clearInterval(spin);
                result.push(numberRandom)
                if (i === slots.length - 1) {
                    if (isVictory(result)){
                        let id = getId(window.location.pathname)
                        fetch("/games/slots/" + id + "/result", {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json',
                                'X-CSRF-Token': document.getElementsByName("_csrf")[0].value
                            },
                            body: JSON.stringify({'stake': currentStake, 'symbols': result})
                        }).then(response => response.json()).then(winResult => {
                            let wonMoney = winResult.wonMoney
                            document.getElementById("balance").innerHTML = 'Balance: ' + winResult.balance
                            for(let symbolIndex of winResult.wonSymbols) {
                                slots[symbolIndex].style.background = '#fa0000';
                            }
                            document.getElementById("result").innerHTML = 'YOU WON ' + wonMoney;

                        });
                    } else {
                        document.getElementById("result").innerHTML = 'YOU LOST';
                    }
                    document.getElementById("button-slot").disabled = false
                }
                return null;
            }
        }, 50)
    }

    function isVictory(result) {
        if(result[0] === result[3] && result[3] === result[6]
            || result[1] === result[4] && result[4] === result[7]
            || result[2] === result[5] && result[5] === result[8]
            || result[0] === result[4] && result[4] === result[8]
            || result[2] === result[4] && result[4] === result[6]) {
            return true;
        }
    }
}


function generaRandom(max){
    return Math.floor((Math.random() *  max));
}

let stakes = [10, 20, 50, 100, 500, 1000]
let currentStake = 10

function increaseStake() {
    let balance = Number(document.getElementById("balance").innerHTML.substring(9))
    if(document.getElementById("less").disabled) {
        document.getElementById("less").disabled = false;
    }
    let index = stakes.indexOf(currentStake)
    currentStake = stakes[index + 1]
    document.getElementById("stake").innerHTML = currentStake.toString()
    if(index === stakes.length - 2) {
        document.getElementById("more").disabled = true
    }
    if(balance < currentStake) {
        document.getElementById("button-slot").disabled = true;
    }
}

function decreaseStake() {
    let balance = Number(document.getElementById("balance").innerHTML.substring(9))
    if(document.getElementById("more").disabled) {
        document.getElementById("more").disabled = false;
    }
    let index = stakes.indexOf(currentStake)
    currentStake = stakes[index - 1]
    document.getElementById("stake").innerHTML = currentStake.toString()
    if(index === 1) {
        document.getElementById("less").disabled = true
    }
    if(balance >= currentStake) {
        document.getElementById("button-slot").disabled = false;
    }
}

function getId(pathname) {
    return pathname.split("/")[3]
}