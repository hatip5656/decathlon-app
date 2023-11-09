INSERT INTO DecathlonPoint (event_name, base_points, result_multiplier, result_exponent)
SELECT 'Long jump',
       0.14354,
       220.0,
       1.4 WHERE NOT EXISTS (
    SELECT 1 FROM  DecathlonPoint WHERE event_name = 'Long jump');