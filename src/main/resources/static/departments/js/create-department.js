document.getElementById("createDepartmentForm").addEventListener("submit", function(event) {
    event.preventDefault();

    const departmentData = {
        code: document.getElementById("code").value,
        name: document.getElementById("name").value,
    };

    axios.post('/api/departments', departmentData)
        .then(response => {
            if (response.status === 201) {
                alert("Department created successfully!");
                document.getElementById("code").value = ""
                document.getElementById("name").value = ""
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred while creating the Department.');
        });
});
