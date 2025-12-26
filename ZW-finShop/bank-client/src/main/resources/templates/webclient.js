const streamErr = e => {
    console.warn("error");
    console.warn(e);
}

fetch("http://localhost:8020/status").then((response) => {
    return can.ndjsonStream(response.body);
}).then(dataStream => {
    const reader = dataStream.getReader();
    const read = result => {
        if (result.done) {
            return;
        }
        render(result.value);
        reader.read().then(read, streamErr);
    }
    reader.read().then(read, streamErr);
});

const render = value => {
    document.getElementById('claimId').textContent = value.id;
    document.getElementById('status').textContent = value.status;
    document.getElementById('period').textContent = value.period;
    document.getElementById('amount').textContent = value.amount;
};