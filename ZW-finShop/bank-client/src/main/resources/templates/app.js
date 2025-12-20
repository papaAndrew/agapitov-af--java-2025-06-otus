
const saveClient = () => {
 const clientData = {
        id: document.getElementById('clientId')?.value || '',
        name: document.getElementById('firstName')?.value || '',
        lastName: document.getElementById('lastName')?.value || '',
        email: document.getElementById('email')?.value || '',
        phone: document.getElementById('phone')?.value || '',
        address: document.getElementById('address')?.value || '',
        // Add other client properties as needed
    };

    // Alternative: If you have all data in a single object
    // const clientData = {
    //     // Your client object properties
    // };

    fetch("http://localhost:8020/client/save", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(clientData)
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

            // If not a redirect, try to parse as JSON
            return response.json().then(data => {
                // Handle JSON response if needed
                console.log("Success:", data);

                // If your controller returns redirect URL in JSON
                if (data.redirectUrl) {
                    window.location.href = data.redirectUrl;
                }
            });
        })
}