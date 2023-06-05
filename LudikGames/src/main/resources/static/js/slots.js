window.addEventListener("load", function () {
    let checks = document.getElementsByClassName("check");

    for(let check of checks) {
        check.addEventListener('change', function () {
            if(check.checked) {
                fetch("/games/slots/favourite", {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                        'X-CSRF-Token': document.getElementsByName("_csrf")[0].value
                    },
                    body: JSON.stringify({'slotId': check.value})
                }).then(r => r.json()).then(resp => console.log(resp))
            } else {
                fetch("/games/slots/favourite", {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json',
                        'X-CSRF-Token': document.getElementsByName("_csrf")[0].value
                    },
                    body: JSON.stringify({'slotId': check.value})
                }).then(r => r.json()).then(resp => console.log(resp))
            }
        })

    }
})
