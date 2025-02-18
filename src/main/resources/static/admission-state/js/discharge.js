    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');
    const patientId = urlParams.get('patientId');


function setBackLink(){
    const createAdmission = document.getElementById('backToList');
    createAdmission.href=`/admission-state?id=${patientId}`
}

document.getElementById("dischargeForm").addEventListener("submit", function(event) {
    event.preventDefault();

    const admissionStateData = {
        dischargeReason: document.getElementById("dischargeReason").value,
        reason: document.getElementById("reason").value,
    };

    axios.put(`/api/admission-state/discharge/${id}`, admissionStateData)
        .then(response => {
       alert("Patient discharged successfully!");
       window.location.href = `/admission-state?id=${patientId}`;
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred while discharging the patient.');
        });
});

    function loadDischargeReasons() {
        axios.get('/api/discharge-reasons')
            .then(response => {
                const dischargeReasonSelect = document.getElementById('dischargeReason');
                dischargeReasonSelect.innerHTML = '<option value="">Select a discharge reason</option>'; // Reset options

                response.data.forEach(reason => {
                    let option = document.createElement('option');
                    option.value = reason; // Assign the enum name as the value
                    option.textContent = reason.replace(/_/g, ' ').toUpperCase(); // Format the display text (optional)
                    dischargeReasonSelect.appendChild(option);
                });
            })
            .catch(error => console.error('Error fetching discharge reasons:', error));
    }

    window.onload = function() {
        setBackLink();
        loadDischargeReasons();
    };


