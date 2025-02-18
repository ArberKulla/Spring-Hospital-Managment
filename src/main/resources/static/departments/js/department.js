        function fetchDepartments() {
            axios.get('/api/departments')
                .then(response => {
                    const departments = response.data;
                    let departmentsTable = document.getElementById('departmentsTable');
                    departmentsTable.innerHTML = "";
                    departments.forEach(department => {
                        let row = `<tr>
                            <td>${department.id}</td>
                            <td>${department.code}</td>
                            <td>${department.name}</td>
                            <td>
                                <button onclick="window.location.href='/departments/edit-department?id=' + ${department.id}">Edit</button>
                                <button onclick="deleteDepartment(${department.id})">Delete</button>
                            </td>
                        </tr>`;
                        departmentsTable.innerHTML += row;
                    });
                })
                .catch(error => console.error('There was an error fetching the departments!', error));
        }

    function deleteDepartment(id) {
        if (confirm('Are you sure you want to delete this department?')) {
            axios.delete('/api/departments/'+id)
                .then(response => {
                    // Handle successful response
                    alert('Department deleted successfully!');
                    // Optionally, you can reload the page to reflect changes:
                    window.location.reload();
                })
                .catch(error => {
                    // Handle error
                    console.error('There was an error deleting the department:', error);
                    alert('There was an error deleting the department. Please try again.');
                });
        }
    }

    function searchDepartmentsCode() {
        const code = document.getElementById('searchByCode').value;

        if (code) {
            axios.get(`/api/departments/searchByCode/${code}`)
                .then(function (response) {
                    updateDepartmentsTable(response.data);
                })
                .catch(function (error) {
                    console.error('There was an error searching by code!', error);
                });
        } else {
            fetchDepartments();
        }
    }

    function searchDepartmentsName() {
        const name = document.getElementById('searchByName').value;

        if (name) {
            axios.get(`/api/departments/searchByName/${name}`)
                .then(function (response) {
                    updateDepartmentsTable(response.data);
                })
                .catch(function (error) {
                    console.error('There was an error searching by name!', error);
                });
        } else {
            fetchDepartments();
        }
    }

    function updateDepartmentsTable(departments) {
        const tbody = document.getElementById('departmentsTable');
        tbody.innerHTML = '';  // Clear the current table data

        // Loop through the departments and add them to the table
        departments.forEach(function (department) {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${department.id}</td>
                <td>${department.code}</td>
                <td>${department.name}</td>
                            <td>
                                <button onclick="window.location.href='/departments/edit-department?id=' + ${department.id}">Edit</button>
                                <button onclick="deleteDepartment(${department.id})">Delete</button>
                            </td>            `;
            tbody.appendChild(row);
        });
    }


    window.onload = function() {
        fetchDepartments();
    };