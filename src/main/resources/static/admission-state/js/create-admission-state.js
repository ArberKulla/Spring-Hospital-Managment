    const urlParams = new URLSearchParams(window.location.search);
    const patientId = urlParams.get('id');

function setBackLink(){
    const createAdmission = document.getElementById('backToList');
    createAdmission.href=`/admission-state?id=${patientId}`
}

document.getElementById("createAdmissionStateForm").addEventListener("submit", function(event) {
    event.preventDefault();

    const admissionStateData = {
        patient: {id: patientId},
        department: {id: document.getElementById("department").value},
        cause: document.getElementById("cause").value,
    };

    axios.post('/api/admission-state', admissionStateData)
        .then(response => {
       alert("Patient admitted successfully!");
       window.location.href = `/admission-state?id=${patientId}`;
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred while creating the patient.');
        });
});

    window.onload = function() {
        setBackLink();
        loadDepartments();
    };

    function loadDepartments() {
        axios.get('/api/departments')
            .then(response => {
                const departmentSelect = document.getElementById('department');
                departmentSelect.innerHTML = '<option value="">Select a department</option>'; // Reset options

                response.data.forEach(department => {
                    let option = document.createElement('option');
                    option.value = department.id;
                    option.textContent = department.name;
                    departmentSelect.appendChild(option);
                });
            })
            .catch(error => console.error('Error fetching departments:', error));
    }
