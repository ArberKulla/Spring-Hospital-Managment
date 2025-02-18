document.getElementById("createPatientForm").addEventListener("submit", function(event) {
    event.preventDefault();

    const patientData = {
        name: document.getElementById("name").value,
        lastName: document.getElementById("lastName").value,
        birthDate: document.getElementById("birthDate").value,
    };

    axios.post('/api/patients', patientData)
        .then(response => {
            if (response.status === 201) {
                alert("Patient created successfully!");
                document.getElementById("name").value = ""
                document.getElementById("lastName").value = ""
                document.getElementById("birthDate").value = ""
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred while creating the patient.');
        });
});
