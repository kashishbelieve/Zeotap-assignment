function toggleInputFields() {
    const source = document.getElementById("source").value;
    document.getElementById("clickhouse-fields").style.display = source === "clickhouse" ? "block" : "none";
    document.getElementById("csv-fields").style.display = source === "csv" ? "block" : "none";
    document.getElementById("status").innerText = "";
}

async function connectClickHouse() {
    const host = document.getElementById("host").value;
    const port = document.getElementById("port").value;
    const database = document.getElementById("database").value;

    const payload = {
        host,
        port: parseInt(port),
        database
    };

    try {
        const response = await fetch("http://localhost:8080/api/connect-clickhouse", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(payload)
        });

        const result = await response.text();
        document.getElementById("status").innerText = result;
    } catch (error) {
        document.getElementById("status").innerText = "❌ Error connecting to ClickHouse.";
        console.error(error);
    }
}



async function uploadCSV() {
    const fileInput = document.getElementById("csvFile");
    const file = fileInput.files[0];

    if (!file) {
        alert("Please select a CSV file!");
        return;
    }

    const formData = new FormData();
    formData.append("file", file);

    try {
        const response = await fetch("http://localhost:8080/api/upload-flatfile", {
            method: "POST",
            body: formData
        });

        const result = await response.text();
        document.getElementById("status").innerText = result;
    } catch (error) {
        document.getElementById("status").innerText = "❌ Failed to upload CSV.";
        console.error(error);
    }
}
