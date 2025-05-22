import requests

def fetch_joke():
    url = "https://official-joke-api.appspot.com/random_joke"
    
    try:
        response = requests.get(url)
        response.raise_for_status()  # Raise error for bad status codes
        joke_data = response.json()

        # Extract joke data
        setup = joke_data.get("setup")
        punchline = joke_data.get("punchline")

        # Display in a user-friendly format
        print("\nHere's a joke for you 🤣")
        print("------------------------")
        print(f"Setup   : {setup}")
        print(f"Punchline: {punchline}")

    except requests.exceptions.RequestException as e:
        print("Error fetching data from the API.")
        print(f"Details: {e}")
    except ValueError:
        print("Error parsing JSON response from the API.")

def main():
    print("=== Joke Generator Using Public API ===")
    while True:
        fetch_joke()
        choice = input("\nDo you want another joke? (y/n): ").strip().lower()
        if choice != 'y':
            print("Thanks for laughing with us! 😂")
            break

if __name__ == "__main__":
    main()
