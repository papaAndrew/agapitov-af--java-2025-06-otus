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
    const statusElement = document.getElementById('status');
    if (statusElement) {
        const text = value.value ? value.value : JSON.stringify(value);
        statusElement.textContent = text;
    }
};