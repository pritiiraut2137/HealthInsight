from flask import Flask, request, jsonify
import numpy as np
import pickle

app = Flask(__name__)
model = pickle.load(open("model.pkl", "rb"))

@app.route("/predict", methods=["POST"])
def predict():
    data = request.json
    features = np.array([[ 
        data["age"], 
        data["bmi"], 
        data["bp"], 
        data["glucose"], 
        data["heart_rate"] 
    ]])
    
    prediction = model.predict(features)[0]
    return jsonify({"risk": int(prediction)})

if __name__ == "__main__":
    app.run(debug=True)
