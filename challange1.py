def sum_prices(items):
    total = 0
    for item in items:
        total += item.get('price', 0)
    return total

# Example usage:
items = [{'item': 'apple', 'price': 1.5}, {'item': 'banana', 'price': 0.75}]
items = [{'item': 'apple', 'price': 2.5}, {'item': 'banana', 'price': 1.75}]
items = [{'item': 'apple', 'price': 21.5}, {'item': 'banana', 'price': 11.75}]
print(sum_prices(items))  # Output: 2.25