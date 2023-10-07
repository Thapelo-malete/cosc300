document.addEventListener('DOMContentLoaded', function () {
    const suicidalPhrases = [
        "i no longer want to live",
        "tired of this life",
        "i have tried",
        "see you on the other side",
        "i can't go on anymore",
        "i don't want to be here",
        "life is not worth living",
        "i feel hopeless",
        "i hate this life",
        "i wish i were dead",
        "nobody would miss me if i were gone",
        "i'm tired of living",
        "i've had enough of this world",
        "i can't live to suffer like this",
        "i hate life",
        "i hate living this life",
        "i'm done living this life",
        "living is not an option",
        "i will return a stronger man",
        "there is no way out",
        "i don't want to live anymore",
        "there is no way out",
        "i want to die",
        "this land is still my home"
    ];

    const inputText = document.getElementById('inputText');
    const analyzeButton = document.getElementById('analyzeButton');
    const result = document.getElementById('result');

    analyzeButton.addEventListener('click', function () {
        const text = inputText.value.toLowerCase();
        let suicidalPhrasesFound = false;

        suicidalPhrases.forEach(phrase => {
            if (text.includes(phrase)) {
                suicidalPhrasesFound = true;
                return; // Exit the loop early if a suicidal phrase is found
            }
        });

        if (text.trim() === '') {
            result.innerHTML = '<span class="red-text">Type or paste a text above.</span>';
        } else if (suicidalPhrasesFound) {
            result.innerHTML = '<span class="red-text">SUICIDAL THOUGHTS or IDEATION IS DETECTED WHICH MAY RESULT IN ONE HARMING THEMSELVES. Seek help from qualified professionals.</span>';
        } else {
            result.innerHTML = '<span class="green-text">NO SUICIDAL IDEATION FOUND WITHIN THE TEXT ABOVE.</span>';
        }
        inputText.style.height = "auto";
        inputText.style.height = inputText.scrollHeight + "px";

        if (result.innerHTML.trim() === "") {
            result.innerHTML = 'Results will be shown here.';
        }
    });
});
