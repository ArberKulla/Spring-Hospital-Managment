    const urlParams = new URLSearchParams(window.location.search);
    const patientId = urlParams.get('id');

    function fetchClinicalData() {
        axios.get('/api/clinical-data/patient/'+patientId)
            .then(response => {
                const clinicalDatas = response.data;
                let clinicalDataTable = document.getElementById('clinicalDataTable');
                clinicalDataTable.innerHTML = "";
                clinicalDatas.forEach(clinicalData => {
                    let row = `<tr>
                        <td>${clinicalData.clinicalRecord}</td>
                        <td>
                            <button onclick="window.location.href='/patients/clinical-data/edit?id=' + ${clinicalData.id} + '&patientId=' + patientId">Edit</button>
                            <button onclick="deleteClinicalData(${clinicalData.id})">Delete</button>
                        </td>
                    </tr>`;
                    clinicalDataTable.innerHTML += row;
                });
            })
            .catch(error => console.error('There was an error fetching the clinical data!', error));
    }

    function deleteClinicalData(id) {
        if (confirm('Are you sure you want to delete this clinical data?')) {
            axios.delete('/api/clinical-data/'+id)
                .then(response => {
                    alert('Clinical Data deleted successfully!');
                    window.location.reload();
                })
                .catch(error => {
                    // Handle error
                    console.error('There was an error deleting the clinical data:', error);
                    alert('There was an error deleting the clinical data. Please try again.');
                });
        }
    }

function searchByClinicalRecord() {
    const clinicalRecord = document.getElementById('searchByRecord').value;

    if (clinicalRecord) {
            axios.get(`/api/clinical-data/patient/record/${patientId}?clinicalRecord=${clinicalRecord}`)
            .then(function (response) {
                updateClinicalDataTable(response.data);
            })
            .catch(function (error) {
                console.error('There was an error searching for clinical data!', error);
            });
    } else {
        fetchClinicalData();
    }
}

function setPatientName(){
    const namePlace = document.getElementById('patientName');

    axios.get('/api/patients/'+patientId)
        .then(response => {
        const patient = response.data;
        namePlace.innerHTML= `${patient.name} ${patient.lastName}`;
     })
     .catch(error => console.error('There was an error fetching the patient data!', error));

}

function updateClinicalDataTable(clinicalDatas) {
    const tbody = document.getElementById('clinicalDataTable');
    tbody.innerHTML = '';

    clinicalDatas.forEach(function (clinicalData) {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${clinicalData.clinicalRecord}</td>
            <td>
                <button onclick="window.location.href='/patients/clinical-data/edit?id=' + ${patientId}">Edit</button>
                <button onclick="deleteClinicalData(${clinicalData.id})">Delete</button>
            </td>
            `;
        tbody.appendChild(row);
    });
}

document.getElementById("createClinicalData").addEventListener("submit", function(event) {
    event.preventDefault();

    const clinicalData = {
        patient: {id: patientId},
        clinicalRecord: document.getElementById("clinicalRecord").value,
    };

    axios.post('/api/clinical-data', clinicalData)
        .then(response => {
            if (response.status === 201) {
                alert("Clinical Data added successfully!");
                location.reload();
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred while adding the Clinical Data.');
        });
});



    window.onload = function() {
        fetchClinicalData();
        setPatientName();
    };