        // Get the patient ID from the URL
        const urlParams = new URLSearchParams(window.location.search);
        const patientId = urlParams.get('id'); // Make sure the URL has the 'id' parameter

        // Fetch patient data using the ID from the URL
        axios.get('/api/patients/'+patientId)
            .then(response => {
                const patient = response.data;
                // Pre-fill the form with the patient's existing data
                document.getElementById("name").value = patient.name;
                document.getElementById("lastName").value = patient.lastName;
                document.getElementById("birthDate").value = patient.birthDate;
            })
            .catch(error => {
                console.error('Error fetching patient data:', error);
                alert('There was an error fetching the patient data.');
            });

        // Submit form (update the patient data)
        document.getElementById("editPatientForm").addEventListener("submit", function(event) {
            event.preventDefault();

            const updatedPatient = {
                id: patientId,
                name: document.getElementById("name").value,
                lastName: document.getElementById("lastName").value,
                birthDate: document.getElementById("birthDate").value
            };

            // Send the updated data to the API
            axios.put(`/api/patients`, updatedPatient)
                .then(response => {
                    if (response.status === 200) {
                        alert("Patient updated successfully!");
                        window.location.href = "/patients";
                    }
                })
                .catch(error => {
                    console.error('Error updating patient:', error);
                    alert('There was an error updating the patient.');
                });
        });