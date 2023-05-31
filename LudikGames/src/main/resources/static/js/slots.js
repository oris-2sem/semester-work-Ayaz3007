function favourite(self) {
    if(self.checked) {
        fetch("/games/slots/favourite", {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-Token': document.getElementsByName("_csrf")[0].value
            },
            body: JSON.stringify({'slotId': self.value})
        }).then(r => r.json()).then(resp => console.log(resp))
    } else {
        fetch("/games/slots/favourite", {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-Token': document.getElementsByName("_csrf")[0].value
            },
            body: JSON.stringify({'slotId': self.value})
        }).then(r => r.json()).then(resp => console.log(resp))
    }

}