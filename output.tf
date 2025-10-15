output "bucket_name" {
  value = aws_s3_bucket.my_test_bucket.bucket
}

output "instance_id" {
  value = aws_instance.ec2_instance.id
}