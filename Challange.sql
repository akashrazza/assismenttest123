-- Challange 1 : Total sales revenue and the number of orders for each month in 2023
SELECT
  to_char(date_trunc('month', order_date), 'YYYY-MM') AS month,
  SUM(total_amount) AS revenue,
  COUNT(*) AS order_count
FROM orders
WHERE order_date >= DATE '2023-01-01'
  AND order_date <  DATE '2024-01-01'
GROUP BY date_trunc('month', order_date)
ORDER BY date_trunc('month', order_date);

-- Challange 2 :  Top 5 customers by total spending, excluding 'Pending' orders

SELECT
  c.customer_id AS customer_id,
  c.first_name AS first_name,
  c.last_name AS last_name,
  SUM(o.total_amount) AS total_spent
FROM customers c
JOIN orders o ON o.customer_id = c.customer_id where o.status != 'Pending'
GROUP BY c.customer_id
ORDER BY total_spent DESC
LIMIT 5;