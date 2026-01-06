
const saveClaim = () => {
    var btn = document.getElementById('btnSend');
    if (btn) {
        if (btn.textContent === 'Новый') {
            btn.textContent = 'Запросить';
            document.getElementById('claimId').textContent = '';
            document.getElementById('amount').value = '';
            document.getElementById('period').value = '';
            document.getElementById('status').textContent = 'Новый';
            return;
        } else {
            btn.textContent = 'Новый';
            document.getElementById('status').textContent = 'Отправляется';
        }
    }

 const loanClaim = {
        clientId: document.getElementById('clientId')?.value || '',
        status: document.getElementById('status')?.value || '',
        period: document.getElementById('period')?.value || '',
        amount: document.getElementById('amount')?.value || '',
    };
    if (!loanClaim.period || !loanClaim.amount) {
        document.getElementById('status').textContent = 'Недостаточно данных';
        return;
    }

    fetch("http://localhost:8020/claim", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(loanClaim)
    })
    .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }

            // Check if the response is a redirect (HTTP 3xx)
            if (response.redirected) {
                window.location.href = response.url;
                return;
            }

            document.getElementById('status').textContent = 'Отправлено';

            // If not a redirect, try to parse as JSON
            return response && response.json().then(data => {
                // Handle JSON response if needed
                console.log("Success:", data);
            });
        })
}