async function predict() {
  const data = {
    age: 45,
    bmi: 28.4,
    bp: 130,
    glucose: 160,
    heart_rate: 85
  };

  const res = await fetch("http://127.0.0.1:5000/predict", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data)
  });

  const result = await res.json();
  document.getElementById("result").innerText =
    result.risk ? "High Risk" : "Low Risk";
}
