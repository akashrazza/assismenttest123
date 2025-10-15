import string

def count_word_frequencies(file_path):
    """
    Count the frequency of each word in a given text file.

    The function reads the file, converts the text to lowercase, removes punctuation,
    and then counts the frequency of each word.

    :param file_path: The path to the text file.
    :return: A dictionary where the keys are the words and the values are the frequencies.
    """
    frequency_dict = {}
    
    with open(file_path, 'r') as file:
        text = file.read().lower()
    
    # Remove punctuation
    text = text.translate(str.maketrans("", "", string.punctuation))
    
    # Split the text into words
    words = text.split()
    
    # Count the frequency of each word
    for word in words:
        if word in frequency_dict:
            frequency_dict[word] += 1
        else:
            frequency_dict[word] = 1
    
    return frequency_dict

# Example usage:
# Assuming the file 'example.txt' contains: "the quick brown fox jumps over the lazy dog the"
print(count_word_frequencies('test.txt'))
# Output: {'the': 3, 'quick': 1, 'brown': 1, 'fox': 1, 'jumps': 1, 'over': 1, 'lazy': 1, 'dog': 1}