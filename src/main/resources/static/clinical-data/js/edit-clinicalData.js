        const urlParams = new URLSearchParams(window.location.search);
        const clinicalDataId = urlParams.get('id');
        const patientId = urlParams.get('patientId');

        // Fetch patient data using the ID from the URL
        axios.get('/api/clinical-data/'+clinicalDataId)
            .then(response => {
                const clinicalData = response.data;
                // Pre-fill the form with the patient's existing data
                document.getElementById("clinicalRecord").value = clinicalData.clinicalRecord;
            })
            .catch(error => {
                console.error('Error fetching clinical data:', error);
                alert('There was an error fetching the clinical data.');
            });

        // Submit form (update the patient data)
        document.getElementById("editClinicalData").addEventListener("submit", function(event) {
            event.preventDefault();

            const updatedClinicalData = {
                clinicalRecord: document.getElementById("clinicalRecord").value
            };

            // Send the updated data to the API
            axios.put(`/api/clinical-data/${clinicalDataId}`, updatedClinicalData)
                .then(response => {
                    if (response.status === 200) {
                        alert("Clinical Data updated successfully!");
                        window.location.href = `/patients/clinical-data?id=${patientId}`;
                    }
                })
                .catch(error => {
                    console.error('Error updating patient:', error);
                    alert('There was an error updating the clinical data.');
                });
        });

        function setPatientLink(){
            const namePlace = document.getElementById('patientLink');
        namePlace.href = `/patients/clinical-data?id=${patientId}`;
        }

            window.onload = function() {
                setPatientLink();
            };