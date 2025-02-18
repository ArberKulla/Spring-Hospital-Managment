        // Get the patient ID from the URL
        const urlParams = new URLSearchParams(window.location.search);
        const departmentId = urlParams.get('id'); // Make sure the URL has the 'id' parameter

        // Fetch patient data using the ID from the URL
        axios.get('/api/departments/'+departmentId)
            .then(response => {
                const department = response.data;
                // Pre-fill the form with the patient's existing data
                document.getElementById("code").value = department.code;
                document.getElementById("name").value = department.name;

            })
            .catch(error => {
                console.error('Error fetching department data:', error);
                alert('There was an error fetching the department data.');
            });

        // Submit form (update the patient data)
        document.getElementById("editDepartmentForm").addEventListener("submit", function(event) {
            event.preventDefault();

            const updatedDepartment = {
                code: document.getElementById("code").value,
                name: document.getElementById("name").value,
            };

            // Send the updated data to the API
            axios.put(`/api/departments/${departmentId}`, updatedDepartment)
                .then(response => {
                    if (response.status === 200) {
                        alert("Department updated successfully!");
                        window.location.href = "/departments";
                    }
                })
                .catch(error => {
                    console.error('Error updating department:', error);
                    alert('There was an error updating the department.');
                });
        });