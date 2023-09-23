class TextAnalyzer {
    constructor() {
        this.suicidalPhrases = [
            "I want to die",
            "Life is not worth living",
            "No one cares about me",
            // Add more phrases as needed
        ];
    }

    analyzeText(text) {
        for (const phrase of this.suicidalPhrases) {
            if (text.toLowerCase().includes(phrase.toLowerCase())) {
                return true; // Suicidal phrase found
            }
        }
        return false; // No suicidal phrase found
    }
}


let button = document.getElementById("analyzeBtn");
let inputText = document.getElementById("inputText");
button.addEventListener("click", ()=>{
    const textAnalyzer = new TextAnalyzer();
    if (textAnalyzer.analyzeText(inputText)) {
        console.log("This text contains suicidal content.");
    } else {
        console.log("This text does not contain any suicidal content.");
    }
})
// Example usage
//function analyzeTheText() {
//    const textAnalyzer = new TextAnalyzer();
//    if (textAnalyzer.analyzeText(inputText)) {
//        console.log("This text contains suicidal content.");
//    } else {
//        console.log("This text does not contain any suicidal content.");
//    }
//}