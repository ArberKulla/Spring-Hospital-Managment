function fetchPatients() {
    axios.get('/api/patients')
        .then(response => {
            const patients = response.data;
            let patientsTable = document.getElementById('patientsTable');
            patientsTable.innerHTML = "";
            patients.forEach(patient => {
                let row = `<tr>
                    <td>${patient.id}</td>
                    <td>${patient.name}</td>
                    <td>${patient.lastName}</td>
                    <td>${patient.birthDate}</td>
                    <td>
                    <button onclick="window.location.href='/admission-state?id=' + ${patient.id}">Next</button>
                    </td>
                    <td>
                    <button onclick="window.location.href='/patients/clinical-data?id=' + ${patient.id}">Next</button>
                    </td>
                    <td>
                        <button onclick="window.location.href='/patients/edit-patient?id=' + ${patient.id}">Edit</button>
                        <button onclick="deletePatient(${patient.id})">Delete</button>
                    </td>
                </tr>`;
                patientsTable.innerHTML += row;
            });
        })
        .catch(error => console.error('There was an error fetching the patients!', error));
}

function deletePatient(id) {
    if (confirm('Are you sure you want to delete this patient?')) {
            axios.delete('/api/patients', {data: {id: id}})
            .then(response => {
                // Handle successful response
                alert('Patient deleted successfully!');
                // Optionally, you can reload the page to reflect changes:
                window.location.reload();
            })
            .catch(error => {
                // Handle error
                console.error('There was an error deleting the patient:', error);
                alert('There was an error deleting the patient. Please try again.');
            });
    }
}

function searchPatients() {
    const name = document.getElementById('searchByName').value;
    const lastName = document.getElementById('searchByLastName').value;

    if (name || lastName) {
        // Construct the URL dynamically based on the search parameters
        let url = '/api/patients/searchByNameAndLastName?';

        if (name) {
            url += `name=${name}&`;
        }

        if (lastName) {
            url += `lastName=${lastName}`;
        }

        // Remove trailing '&' if there's one
        url = url.endsWith('&') ? url.slice(0, -1) : url;

        axios.get(url)
            .then(function (response) {
                updatePatientsTable(response.data);
            })
            .catch(function (error) {
                console.error('There was an error searching for patients!', error);
            });
    } else {
        fetchPatients();
    }
}

function updatePatientsTable(patients) {
    const tbody = document.getElementById('patientsTable');
    tbody.innerHTML = '';  // Clear the current table data

    // Loop through the patients and add them to the table
    patients.forEach(function (patient) {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${patient.id}</td>
            <td>${patient.name}</td>
            <td>${patient.lastName}</td>
            <td>${patient.birthDate}</td>
            <td>                            <button onclick="window.location.href='/patients/clinical-data?id=' + ${patient.id}">Next</button></td>
            <td>
                <button onclick="window.location.href='/patients/edit-patient?id=' + ${patient.id}">Edit</button>
                <button onclick="deletePatient(${patient.id})">Delete</button>
            </td>
        `;
        tbody.appendChild(row);
    });
}


window.onload = function() {
    fetchPatients();
};