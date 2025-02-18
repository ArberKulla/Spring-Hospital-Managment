
    const urlParams = new URLSearchParams(window.location.search);
    const patientId = urlParams.get('id');

    function fetchAdmissionStateData() {
        axios.get('/api/admission-state/patient/'+patientId)
            .then(response => {
                const admissionStates = response.data;
                let admissionStateTable = document.getElementById('admissionStateTable');
                admissionStateTable.innerHTML = "";
                admissionStates.forEach(admissionState => {
                let extraButton = admissionState.discharge
                    ? ""
                    : `<button onclick="window.location.href='/admission-state/discharge?id=' + ${admissionState.id} + '&patientId=' + patientId">Discharge</button>
                        <button onclick="window.location.href='/admission-state/edit-department?id=' + ${admissionState.id} + '&patientId=' + patientId">Change Department</button>`
                    ;

                    let row = `<tr>
                        <td>${admissionState.id}</td>
                        <td>${admissionState.department.name}</td>
                        <td>${formatDate(admissionState.enteringDate)}</td>
                        <td>${formatDate(admissionState.exitingDate)}</td>
                        <td>${admissionState.cause}</td>
                        <td>${admissionState.reason}</td>
                        <td>${admissionState.discharge}</td>
                        <td>${admissionState.dischargeReason}</td>
                        <td>
                            ${extraButton}
                            <button onclick="deleteAdmissionState(${admissionState.id})">Delete</button>
                        </td>
                    </tr>`;
                    admissionStateTable.innerHTML += row;
                });
            })
            .catch(error => console.error('There was an error fetching the admission state!', error));
    }

    function deleteAdmissionState(id) {
        if (confirm('Are you sure you want to delete this admission state?')) {
            axios.delete('/api/admission-state/'+id)
                .then(response => {
                    alert('Admission State deleted successfully!');
                    window.location.reload();
                })
                .catch(error => {
                    // Handle error
                    console.error('There was an error deleting the admission state:', error);
                    alert('There was an error defleting the admission state. Please try again.');
                });
        }
    }

function searchByClinicalRecord() {
    const clinicalRecord = document.getElementById('searchByRecord').value;

    if (clinicalRecord) {
            axios.get(`/api/admission-state/patient/record/${patientId}?clinicalRecord=${clinicalRecord}`)
            .then(function (response) {
                updateAdmissionStateTable(response.data);
            })
            .catch(function (error) {
                console.error('There was an error searching for admission state!', error);
            });
    } else {
        fetchAdmissionState();
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

function setCreateLink(){
    const createAdmission = document.getElementById('createAdmissionState');
    axios.get('/api/admission-state/is-discharged/'+patientId)
        .then(response => {
        if(!response.data){
        createAdmission.href=`/admission-state/create-admission-state?id=${patientId}`
        createAdmission.innerHTML= `Admit Into Hospital`;
        }
     })
     .catch(error => console.error('There was an error fetching the patient data!', error));


    }

function updateAdmissionStateTable(admissionStates) {
    const tbody = document.getElementById('admissionStateTable');
    tbody.innerHTML = '';

    admissionStates.forEach(function (admissionState) {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${admissionState.clinicalRecord}</td>
            <td>
                <button onclick="window.location.href='/patients/admission-state/edit?id=' + ${patientId}">Edit</button>
                <button onclick="deleteAdmissionState(${admissionState.id})">Delete</button>
            </td>
            `;
        tbody.appendChild(row);
    });
}

function formatDate(isoDate) {

    if (!isoDate) return 'null'; // Handle null or undefined dates
    const date = new Date(isoDate);
    return date.toLocaleString('en-US', {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit'
    });
}


    window.onload = function() {
        fetchAdmissionStateData();
        setPatientName();
        setCreateLink();
    };